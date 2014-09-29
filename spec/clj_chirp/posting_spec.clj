(ns clj-chirp.posting-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.posting :refer :all]
            [clj-time.core :as time]))

(describe "clj-chirp.posting"
          (with timestamp (time/date-time 2014))
          (with content "post")
          (with user "user")
          (describe "User does not exist yet."
                    (with posts-with-existing-user [{:user-name @user :posts-values [{:content @content :timestamp @timestamp}]}])
                    (with already-existing-user "already existing user")
                    (it "User is added."
                        (should= @posts-with-existing-user
                                 (post [] {:user-name @user :content @content} @timestamp)))
                    (it "New user is added when another user already exists."
                        (should= (conj @posts-with-existing-user
                                       {:user-name @already-existing-user :posts-values [{:content @content :timestamp @timestamp}]})
                                 (post @posts-with-existing-user {:user-name @already-existing-user :content @content} @timestamp))))
          (describe "User already exists."
                    (with added-content "added post")
                    (it "User is updated."
                        (should= [{:user-name @user :posts-values [{:content @content :timestamp @timestamp}
                                                            {:content @added-content :timestamp @timestamp}]}]
                                 (post [{:user-name @user :posts-values [{:content @content :timestamp @timestamp}]}]
                                       {:user-name @user :content @added-content} @timestamp)))))