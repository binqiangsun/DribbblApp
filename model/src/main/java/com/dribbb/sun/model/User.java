package com.dribbb.sun.model;

/**
 * Created by sunbinqiang on 16/2/2.
 */
public class User {
    private int id;
    private String name;
    private String username;
    private String html_url;
    private String avatar_url;
    private String bio;
    private String location;
    /**
     * web : http://www.creativemints.com/
     * twitter : https://twitter.com/creativemints
     */

    private LinksEntity links;
    private int buckets_count;
    private int comments_received_count;
    private int followers_count;
    private int followings_count;
    private int likes_count;
    private int likes_received_count;
    private int projects_count;
    private int rebounds_received_count;
    private int shots_count;
    private int teams_count;
    private boolean can_upload_shot;
    private String type;
    private boolean pro;
    private String buckets_url;
    private String followers_url;
    private String following_url;
    private String likes_url;
    private String projects_url;
    private String shots_url;
    private String teams_url;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public LinksEntity getLinks() {
        return links;
    }

    public int getBuckets_count() {
        return buckets_count;
    }

    public int getComments_received_count() {
        return comments_received_count;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public int getFollowings_count() {
        return followings_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public int getLikes_received_count() {
        return likes_received_count;
    }

    public int getProjects_count() {
        return projects_count;
    }

    public int getRebounds_received_count() {
        return rebounds_received_count;
    }

    public int getShots_count() {
        return shots_count;
    }

    public int getTeams_count() {
        return teams_count;
    }

    public boolean isCan_upload_shot() {
        return can_upload_shot;
    }

    public String getType() {
        return type;
    }

    public boolean isPro() {
        return pro;
    }

    public String getBuckets_url() {
        return buckets_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public String getLikes_url() {
        return likes_url;
    }

    public String getProjects_url() {
        return projects_url;
    }

    public String getShots_url() {
        return shots_url;
    }

    public String getTeams_url() {
        return teams_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public static class LinksEntity {
        private String web;
        private String twitter;

        public void setWeb(String web) {
            this.web = web;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        public String getWeb() {
            return web;
        }

        public String getTwitter() {
            return twitter;
        }
    }
}
