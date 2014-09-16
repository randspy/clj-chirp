(ns clj-chirp.find)

(defn find-by-user-name [posts user]
  (first (filter #(= (:user-name %) user) posts)))
