(defproject text-cluster "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.rpl/specter "0.9.2"]
                 [incanter "1.5.7"]
                 [org.clojure/math.combinatorics "0.1.4"]
                 [rm-hull/clustering "0.1.3"]
                 [org.clojure/data.priority-map "0.0.7"]]
  :main ^:skip-aot text-cluster.core
  :resource-paths ["lib/freeling-4.0.0.jar"]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
