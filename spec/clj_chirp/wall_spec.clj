(ns clj-chirp.wall-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.wall :refer :all]
            [clj-time.core :as time]))

(describe "clj-chirp.wall"
          (with user "user")
          (describe "There are no user posts."
                    (it "Returns nothing."
                        (should= []
                                 (show-wall [] @user))))
          (describe "User is not following anybody."
                    (with not-followed-user "not followed user")
                    (with content "content")
                    (it "Returns user posts."
                        (should= [{:user-name @user :content @content}]
                                 (show-wall [{:user-name @user :posts-values [{:content @content}]}
                                             {:user-name @not-followed-user}]
                                            @user))))
          (describe "User is following other users."
                    (with followed-user "followed user")
                    (with content "content")
                    (it "Returns user posts and followed users."
                        (should= [{:user-name @user :content @content}
                                  {:user-name @followed-user :content @content}]
                                 (show-wall [{:user-name @user
                                              :follows [@followed-user]
                                              :posts-values [{:content @content}]}
                                             {:user-name @followed-user
                                              :posts-values [{:content @content}]}]
                                            @user)))))