package com.example.androidpodcastplayer.model;

/**
{
    "rss":{
        "channel":{
            "link":[
                {
                "_href":"http://billmaher.hbo.libsynpro.com/rss",
                "_rel":"self",
                "_type":"application/rss+xml",
                "__prefix":"atom"
                },
                "http://www.hbo.com/real-time-with-bill-maher"
            ],
            "title":"Real Time with Bill Maher",
            "pubDate":"Mon, 08 Aug 2016 22:22:09 +0000",
            "lastBuildDate":"Tue, 09 Aug 2016 02:40:48 +0000",
            "generator":"Libsyn WebEngine 2.0",
            "language":"en-us",
            "copyright":{
                "__cdata":"(c) Home Box Office"
            },
            "docs":"http://www.hbo.com/real-time-with-bill-maher",
            "managingEditor":"podcasts@hbo.com (podcasts@hbo.com)",
            "summary":{
                "__prefix":"itunes",
                "__cdata":"Download and watch full episodes of Real Time with Bill Maher including his New Rules and Overtime segments with his guest panelists. New episodes of Real Time with Bill Maher air Fridays at 10, only on HBO."
            },
            "image":[
                {
                    "url":"http://static.libsyn.com/p/assets/a/0/6/1/a061ceb8595319af/billmaher_logo1400.jpg",
                    "title":"Real Time with Bill Maher",
                    "link":{
                        "__cdata":"http://www.hbo.com/real-time-with-bill-maher"
                        }
                },
                {
                    "_href":"http://static.libsyn.com/p/assets/a/0/6/1/a061ceb8595319af/billmaher_logo1400.jpg",
                    "__prefix":"itunes"
                }
            ],
            "author":{
                "__prefix":"itunes",
                "__text":"HBO Podcasts"
            },
            "category":{
                "_text":"News & Politics",
                "__prefix":"itunes"
            },
                "explicit":{
                "__prefix":"itunes",
                "__text":"yes"
            },
            "owner":{
                "name":{
                    "__prefix":"itunes",
                    "__cdata":"HBO Podcasts"
                },
                "email":{
                    "__prefix":"itunes",
                    "__text":"podcasts@hbo.com"
                },
                "__prefix":"itunes"
            },
            "description":{
                "__cdata":"Download and watch full episodes of Real Time with Bill Maher including his New Rules and Overtime segments with his guest panelists. New episodes of Real Time with Bill Maher air Fridays at 10, only on HBO."
            },
            "subtitle":{
                "__prefix":"itunes",
                "__cdata":"Download and watch full episodes of Real Time with Bill Maher including his New Rules and Overtimes"
            },
            "new-feed-url":{
                "__prefix":"itunes",
                "__text":"http://billmaher.hbo.libsynpro.com/rss"
            },
            "item":[  ]
        },
        "_xmlns:atom":"http://www.w3.org/2005/Atom",
        "_xmlns:cc":"http://web.resource.org/cc/",
        "_xmlns:itunes":"http://www.itunes.com/dtds/podcast-1.0.dtd",
        "_xmlns:media":"http://search.yahoo.com/mrss/",
        "_xmlns:rdf":"http://www.w3.org/1999/02/22-rdf-syntax-ns#",
        "_version":"2.0"
        }
    }
}
*/

public class Rss {
    private Channel channel;

    public Rss() {  }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}
