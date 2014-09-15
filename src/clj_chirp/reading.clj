(ns clj-chirp.reading
  (:require [clj-chirp.find :as find]))

(defn read-user-posts [posts user-name]
  (if (empty? posts)
    {}
    (let [user-posts (find/find-by-user posts user-name)]
      (if (nil? user-posts)
        {}
        user-posts))))