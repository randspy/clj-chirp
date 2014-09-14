(ns clj-chirp.reading)


(defn- user-content [posts user]
  (first (filter #(= (:user %) user) posts)))

(defn read-user-posts [posts user-name]
  (if (empty? posts) {} (user-content posts user-name)))