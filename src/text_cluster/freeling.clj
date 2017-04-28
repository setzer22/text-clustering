(ns text-cluster.freeling
  (:require [clojure.data.json :as json]
            [com.rpl.specter :refer :all]
            [clojure.string :as string]
            [text-cluster.utils :refer :all]
            [text-cluster.default-options :as options])
  (:import [edu.upc.freeling Tagset Dictionary Analyzer Summarizer ConfigOptions InvokeOptions Document OutputJson OutputXml AnalysisLevel TaggerAlgorithm DependencyParser WSDAlgorithm ForceSelectStrategy]))


(def ^:dynamic freeling-dir-path
  "The freeling data installation path"
  "/usr/local/share/freeling/")

(defonce analyzers (atom {}))

(defn ^:private mk-analyzer [lang]
  (let [config-options (options/mk-config-options freeling-dir-path lang)]
    (Analyzer. config-options)))

(defn get-analyzer [lang]
  (if (not (get @analyzers lang))
    (swap! analyzers assoc lang (mk-analyzer lang))
    (get @analyzers lang)))

(defn get-dictionary [lang]
  (let [analyzer (get-analyzer lang)]
    (.getDictionary (.getMaco analyzer))))

(defn get-semantic-db [lang]
  (let [analyzer (get-analyzer lang)]
    (.getSemanticDB (.getSenses analyzer))))

(defn init-freeling [lang]
  (try
    (defonce --auto-1309184124-test-1p044340124 ; A symbol we won't re-use
      (do (clojure.lang.RT/loadLibrary "freeling_javaAPI")
          (edu.upc.freeling.Util/initLocale "default")))
    (catch java.lang.UnsatisfiedLinkError e
      (throw (Exception.
              (str "No freeling installation was found in java.library.path. Try"
                   "setting LD_LIBRARY_PATH environment variable to freeling libs.")))))
  (get-analyzer lang))

(defonce outputs (atom {}))

(defn output-fn [lang]
  (if (not (get @outputs lang))
    (swap! outputs assoc lang (OutputJson. (str-or-throw freeling-dir-path "/" lang "/json.cfg"))))
  {"json" #(.printResults (get @outputs lang) %)
   "xml" #(.printResults (OutputXml.) %)})

;;TODO: Thread safety
(defn analyze [& {:keys [text lang output level]
                 :or {output "json", lang "en", level "semgraph"}}]
  (try
    (defonce --auto-1309184124-test-1p044340124 ; A symbol we won't re-use
     (do (clojure.lang.RT/loadLibrary "freeling_javaAPI")
         (edu.upc.freeling.Util/initLocale "default")))

    (catch java.lang.UnsatisfiedLinkError e
      (throw (Exception.
              (str "The textserver is down and no local intsall of freeling "
                   "was found on java.library.path. Please fix either of those errors.")))))
  (if (not (get @analyzers lang)) (swap! analyzers assoc lang (mk-analyzer lang)))
  (let [analyzer (get @analyzers lang)
        doc (Document.)]
    (.setCurrentInvokeOptions analyzer (options/mk-invoke-options level))
    (.analyze analyzer text doc true)
    {:json (clojure.walk/keywordize-keys (json/read-str  (((output-fn lang) "json") doc)))
     :document doc}))

