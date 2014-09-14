(ns clj-chirp.posting)

(defn- user-content [posts user]
  (first (filter #(= (:user %) user) posts)))

(defn- compute-new-user-element [post timestamp]
  (assoc {} :user (:user post)
            :posts [(assoc {} :content (:content post)
                              :timestamp timestamp)]))

(defn- add-post-to-user-element [user post timestamp]
  (assoc {} :user (:user post)
            :posts (conj (:posts user)
                         {:content (:content post) :timestamp timestamp})))

(defn post [posts new-post timestamp]
  (let [user (user-content posts (:user new-post))]
    (if user
      (assoc posts (.indexOf posts user)
                   (add-post-to-user-element user new-post timestamp))
      (conj posts (compute-new-user-element new-post timestamp)))))
