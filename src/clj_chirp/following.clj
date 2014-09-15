(ns clj-chirp.following)

(defn- user-content [posts user]
  (first (filter #(= (:user %) user) posts)))

(defn add-follow-element [user followed-user]
  (let [distinct-follows (vec (set (conj (:follows user) (:user followed-user))))
        follows-without-user (filter #(not= % (:user user)) distinct-follows)]
    (if (seq follows-without-user)
      (assoc user :follows follows-without-user)
      user)))

(defn follows [existing-posts new-post]
  (let [user (user-content existing-posts (:user new-post))
        followed-user (user-content existing-posts (:content new-post))]
    (if (and user followed-user)
      (assoc existing-posts (.indexOf existing-posts user)
                            (add-follow-element user followed-user))
      existing-posts)))