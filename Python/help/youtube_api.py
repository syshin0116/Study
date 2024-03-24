import argparse
import requests
from dotenv import load_dotenv, find_dotenv
import os
from googleapiclient.discovery import build
from googleapiclient.errors import HttpError
from pprint import pprint

load_dotenv(find_dotenv())
YOUTUBE_API_KEY = os.environ['YOUTUBE_API_KEY']

# Set DEVELOPER_KEY to the API key value from the APIs & auth > Registered apps
# tab of
#   https://cloud.google.com/console
# Please ensure that you have enabled the YouTube Data API for your project.
DEVELOPER_KEY = YOUTUBE_API_KEY
YOUTUBE_API_SERVICE_NAME = 'youtube'
YOUTUBE_API_VERSION = 'v3'

youtube = build(YOUTUBE_API_SERVICE_NAME, YOUTUBE_API_VERSION, developerKey=DEVELOPER_KEY)

def youtube_search(youtube, options):
    # Call the search.list method to retrieve results matching the specified
    # query term.
    search_response = youtube.search().list(
        q=options.q,
        part='id,snippet',
        maxResults=options.max_results,
        order=options.order,
        type=options.type
        ).execute()

    videos = []
    channels = []
    playlists = []
    video_ids = []
    # Add each result to the appropriate list, and then display the lists of
    # matching videos, channels, and playlists.
    pprint(search_response)
    for search_result in search_response.get('items', []):
        video = {
            'id': None,
            'title': None,
            'views': None,
            'country': None,
            'likes': None,
            'dislikes': None,
            'Channel': None,
            
        }
        if search_result['id']['kind'] == 'youtube#video':
            videos.append('%s (%s)(%s)' % (search_result['snippet']['title'],
                                    search_result['id']['videoId'],
                                    search_result['snippet']['publishedAt']))
            video_ids.append(search_result['id']['videoId'])
        elif search_result['id']['kind'] == 'youtube#channel':
            channels.append('%s (%s)' % (search_result['snippet']['title'],
                                    search_result['id']['channelId']))
        elif search_result['id']['kind'] == 'youtube#playlist':
            playlists.append('%s (%s)' % (search_result['snippet']['title'],
                                        search_result['id']['playlistId']))

    return
    if videos:
        print ('Videos:\n', '\n'.join(videos), '\n')
    if channels:
        print ('Channels:\n', '\n'.join(channels), '\n')
    if playlists:
        print ('Playlists:\n', '\n'.join(playlists), '\n')

    youtube_video(video_ids)
    

def youtube_video(youtube, id, options):
    search_response = youtube.video().list(
    part='id,snippet',
    maxResults=options.max_results,
    order=options.order,
    type=options.type
    ).execute()

if __name__ =='__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--q', help='Search term', default='Google')
    parser.add_argument('--max-results', help='Max results', default=25, type=int)
    parser.add_argument('--order', help='order by:(date, rating, relevance, title, videoCount, viewCount)', default='viewCount', type=str)
    parser.add_argument('--type', help='video, playlist, channel')
    args = parser.parse_args()

    try:
        # print(f'arguments: {args}')
        youtube_search(youtube, args)    
    except HttpError as e:  # Corrected syntax for Python 3
        print('An HTTP error %d occurred:\n%s' % (e.resp.status, e.content))
