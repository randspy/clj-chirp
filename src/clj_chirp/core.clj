(ns clj-chirp.core
  (:require [clj-chirp.usecases :as usecases]
            [clj-chirp.posting :as posting]
            [clj-chirp.time :as time])
  (:gen-class))

(def all-posts (atom []))

(defn print-prompt []
  (print "> ")
  (flush))

(defn exit-program? [user-input]
  (= (.toUpperCase user-input) "EXIT"))

(defn console-loop []
  (loop []
    (print-prompt)
    (let [user-input (read-line)]
      (if (exit-program? user-input)
        (println "FINISHED")
        (do (usecases/usecase-execution all-posts user-input (time/current-time))
            (recur))))))

(defn -main [& args]
  (console-loop))