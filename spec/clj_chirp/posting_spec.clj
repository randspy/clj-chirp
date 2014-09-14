(ns clj-chirp.posting-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.posting :refer :all]
            [clj-time.core :as time]))

(describe "Post is saved."
          (with timestamp (time/date-time 2014))
          (with posts-with-existing-user [{:user "user bob" :posts [{:content "post" :timestamp @timestamp}]}])
          (describe "User does not exist yet."
                    (it "User is added."
                        (should= @posts-with-existing-user
                                 (post [] {:user "user bob" :content "post"} @timestamp)))
                    (it "New user is added with already existing user."
                        (should= (conj @posts-with-existing-user
                                       {:user "user robert" :posts [{:content "post" :timestamp @timestamp}]})
                                 (post @posts-with-existing-user {:user "user robert" :content "post"} @timestamp)))
                    (it "User already exists."
                        (should= [{:user "user bob" :posts [{:content "post" :timestamp @timestamp}
                                                            {:content "post added" :timestamp @timestamp}]}]
                                 (post [{:user "user bob" :posts [{:content "post" :timestamp @timestamp}]}]
                                       {:user "user bob" :content "post added"} @timestamp)))))