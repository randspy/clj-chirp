(ns clj-chirp.core
  (:require [clj-chirp.content :as content]
            [clj-chirp.posting :as posting]
            [clj-chirp.time :as time])
  (:gen-class))

(defn exit-program? [user-input]
  (= (.toUpperCase user-input) "EXIT"))

(def all-posts (atom []))

(defn console-loop []
  (loop []
    (let [user-input (read-line)]
      (if (exit-program? user-input)
        (println "FINISHED")
        (do (let [user-data (content/split-user-from-content user-input "->")
                  posts (posting/post @all-posts user-data (time/current-time))]
              (reset! all-posts posts))
            (println @all-posts)
            (recur))))))

(defn -main [& args]
  (console-loop))