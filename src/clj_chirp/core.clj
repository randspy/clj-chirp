(ns clj-chirp.core
  (:require [clj-chirp.content :as content]
            [clj-chirp.posting :as posting]
            [clj-chirp.time :as time])
  (:gen-class))

(defn exit-program? [user-input]
  (= (.toUpperCase user-input) "EXIT"))

(def all-posts (atom []))

(defn usecase-execution [posts user-input]
  (let [user-input-after-split (content/split-user-from-content user-input "->")
        user-posts (posting/post @posts user-input-after-split (time/current-time))]
    (reset! posts user-posts)))

(defn console-loop []
  (loop []
    (let [user-input (read-line)]
      (if (exit-program? user-input)
        (println "FINISHED")
        (do (usecase-execution all-posts user-input)
            (println @all-posts)
            (recur))))))

(defn -main [& args]
  (console-loop))