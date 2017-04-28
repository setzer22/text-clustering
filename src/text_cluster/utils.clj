(ns text-cluster.utils)

(defn StringList->seq [s]
  "Converts a StringList object to a regular seq. The original object is destroyed"
  (if (pos? (.size s))
    (lazy-seq
     (cons (.getFirst s)
           (do (.clearFirst s)
               (StringList->seq s))))
    nil))

(defn str-or-throw [& args]
  (let [s (apply str args)
        f (java.io.File. s)]
    (if (not (.exists f))
      (throw (java.io.FileNotFoundException.
              (str "Freeling error: File " s " was not found in the filesystem."
                   "This happened due to the requested language not being fully"
                   "supported by the current freeling installation.")))
      s)))
