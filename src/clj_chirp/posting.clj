(ns clj-chirp.posting
  (:require [clj-chirp.find :as find]))

(defn- compute-new-user-element [post timestamp]
  (assoc {} :user-name (:user-name post)
            :posts [(assoc {} :content (:content post)
                              :timestamp timestamp)]))

(defn- add-post-to-user-element [user post timestamp]
  (assoc user :posts (conj (:posts user)
                         {:content (:content post) :timestamp timestamp})))

(defn post [posts new-post timestamp]
  (let [user (find/find-by-user posts (:user-name new-post))]
    (if user
      (assoc posts (.indexOf posts user)
                   (add-post-to-user-element user new-post timestamp))
      (conj posts (compute-new-user-element new-post timestamp)))))
