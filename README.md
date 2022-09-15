<img src="dilan-banner.png" style="margin-left: auto; margin-right: auto" alt="banner">


<h2 align="center">
<a href="https://discord.com/api/oauth2/authorize?client_id=913511878523752519&permissions=274914725120&scope=bot">Add the bot to your server by clicking this link!</a>
</h2>

# Important note
This bot is now in "maintenance mode" - this means that it will receive only dependency updates and no new features.  
This is due to the codebase being very cluttered and in need of a refactor/rewrite, but because I'm currently working on other projects and learning mainly web/backend development, rewriting this bot's code is not a priority to me.

## Features and usage
The bot's default prefix is "dilan", every command uses this pattern:  
`[prefix] [command] [arguments]`

- `play [song title]` - plays the requested song from YouTube, or puts it in the queue if something is already playing
- `play` - resumes the track if paused
- `pause` - pauses the track
- `skip` - skips to the next track in the queue
- `clear` - clears the queue
- `stop` - stops the music and clears the queue
- `np` - shows what track is currently playing
- `queue [page number]` - shows the specified page of the queue. If the page number is empty, it shows the first page.
- `remove [track number]` - removes the specified track from the queue. If the track number is empty, it removes the last track in the queue
- `loop` - enables or disabled looping of the currently playing track.
- `disconnect` - disconnects the bot from a voice chat
- `lyrics [song title]` - shows lyrics for the specified song. If the song title is empty, it shows lyrics for what is currently playing
- `8ball [question]` - asks the magic 8-ball the specified question
- `reddit [subreddit]` - shows a random post from the specified subreddit
- `prefix [yourprefix]` - changes the prefix of the bot to the one specified

## FAQ
- Does the bot store my information?
    - No, we don't store any user-related data.  
    The server's ID, however, goes into our database, to enable our users to change the prefix of the bot. That's all.


- I kicked the bot from my server and added it back, but it won't react to any commands!
    - There's a high chance that you've changed the bot's prefix!  
    The prefix gets reset everytime you add the bot back to your server, you can change it back to your custom one using 
    `dilan prefix [yourprefix]`  
    If the issue still persists, feel free to open an issue on this GitHub repo.


## How to host the bot yourself
Before you start, you'll need to create an access token:

- Go to [Discord Developer Portal](https://discord.com/developers/applications) and create a new application.
- Go to the `BOT` tab on the left and click `create a bot`
- Scroll down to `Privileged Gateway Intents` and enable all the options
- Scroll down to `Bot Permissions` and select `Administrator` (or the permissions that are set in the invite link above)
- Now, go to `OAuth2 -> Url Generator` on the left of the screen
- Select `bot`, then `Administrator` (or the permissions that are set in the invite link above)
- What you've ended up with is an invitation link that you can use to add the bot to your Discord server!

Now you can deploy the bot by using one of the following methods:

- A Docker Compose image
- A regular Docker image with a database provided by you

#### Docker Compose method
1. Download the `docker-compose.yml` file from this repo and save it in a new directory
2. Modify the file to contain your Discord bot token.  
I also **STRONGLY** advise changing the default database credentials!
3. Run the following command in the directory containing the file: `docker-compose up`

That's it, you should have a fully configured and working instance of Dilan running on your machine!

#### Regular Docker image method
Here's a command that you'll have to run in order to start the bot up on your machine:

```
docker run \
            -e SPRING.DATASOURCE.URL=jdbc:postgresql://yourdatabaseurl \
            -e SPRING.DATASOURCE.USERNAME=yourdatabaseusername \
            -e SPRING.DATASOURCE.PASSWORD=yourdatabasepassword \
            -e DISCORD.TOKEN=yourdiscordbottoken \
            eukon/dilan
```
Please note that the command above is intended for Linux hosts.  
If you want to use Windows, replace the `` \ `` with `` ` ``

## Contributing
If you have an idea for a new feature or a bugfix, feel free to open an issue or a pull request!

## Credits
This bot is using the following libraries:

- [Spring Framework](https://spring.io)

- [Javacord](https://javacord.org/)

- [LavaPlayer Fork](https://github.com/Walkyst/lavaplayer-fork) by Walkyst

- [GeniusLyricsApi](https://github.com/LowLevelSubmarine/GeniusLyricsAPI) by LowLevelSubmarine

- [Gson](https://github.com/google/gson)

- [Unirest For Java](http://kong.github.io/unirest-java/) by Kong

- [PostgreSQL](https://www.postgresql.org/)

Big shout out to everyone who has contributed to the projects listed above, without you, Dilan would not be possible!