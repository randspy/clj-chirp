(defproject clj-chirp "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-time "0.8.0"]]
  :profiles {:dev {:dependencies [[speclj "3.1.0"]
                                  [speclj-notify-osd "0.0.2"]]}}
  :plugins [[speclj "3.1.0"]]
  :test-paths ["spec"]
  :main clj-chirp.core
  :aot [clj-chirp.core])
