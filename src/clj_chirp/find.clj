(ns clj-chirp.find)

(defn find-by-user [posts user]
  (first (filter #(= (:user-name %) user) posts)))
