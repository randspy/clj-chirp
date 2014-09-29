(ns clj-chirp.reading
  (:require [clj-chirp.find :as find]))

(defn read-user-posts [all-users-posts user-name]
  (if (seq all-users-posts)
    (let [user-posts (find/find-by-user-name all-users-posts user-name)]
      (if (seq user-posts)
        user-posts
        {}))
    {}))