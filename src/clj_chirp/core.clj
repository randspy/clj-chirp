(ns clj-chirp.core
  (:require [clj-chirp.usecases :as usecases]
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
        (do (usecases/usecase-execution all-posts user-input (time/current-time))
            (println @all-posts)
            (recur))))))

(defn -main [& args]
  (console-loop))