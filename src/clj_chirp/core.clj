(ns clj-chirp.core
  (:gen-class))

(defn exit-program? [user-input]
  (= (.toUpperCase user-input) "EXIT"))

(defn console-loop []
  (loop []
    (let [user-input (read-line)]
      (if (exit-program? user-input)
        (println "FINISHED")
        (do (println user-input)
            (recur))))))

(defn -main [& args]
  (console-loop))