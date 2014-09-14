(ns clj-chirp.reading)


(defn- user-content [posts user]
  (first (filter #(= (:user %) user) posts)))

(defn read-user-posts [posts user-name]
  (if (empty? posts)
    {}
    (let [user-posts (user-content posts user-name)]
      (if (nil? user-posts)
        {}
        user-posts))))