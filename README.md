# Avengers Discord Bot
A multipurpose bot to learn about your favorite Marvel heroes, play music, define words, and moderate your channel! This bot can also be helpful for people who want to learn how to make a Discord Bot using the JDA library. I made this so that new users can look at the methods that I've used for various commands as well as to give some ideas for their own Discord Bot.

## What I Used to Build This Bot
There are several APIs that I've used for this Bot <br />
• [JDA](https://github.com/DV8FromTheWorld/JDA) (Provides wrapping of the Discord REST API) <br />
• [Marvel Comics](https://developer.marvel.com/) (A library that accesses information about Marvel's  heroes and comics) <br />
• [LavaPlayer](https://github.com/sedmelluq/lavaplayer) (The audio player libary for Discord) <br />
• [jsoup](https://github.com/jhy/jsoup) (Java HTML Parser used for webscraping Urban Dictionary)

## Commands
Please ignore the [] when entering the command. It just shows what should be there when entering the command.
```
For Fun Module
!snap - Deletes half of the messages in the channel. (Note: This command currently does not bypass the 100 message and 2-week old limit)
!jarvis define [Word] - Provide the Urban Dictionary definition of the word.

Comic Module
!comic [Character Name] - Provides biography of character and a list of comics in which that character appears in

Moderation/Admin Module
!thor [@username] - Bans the user from the server for 1 day
!shoot [@username] - Kick the user from the channel

Music Module
!jarvis play [YouTube url of song/playlist] - Plays the song/playlist in the current voice channel
!cap - Currently plays either "Star-Spangled Man" or "It's Been a Long, Long, Time"
!smash - Plays happy songs at random from "Feelin Good" Playlist
!shutdown - Clears the queue and stops the current player
!leave - The Bot leaves the voice channel
```

## TO DO
• Add more commands with other Heroes/Villains in the MCU <br />
• Add more functionality 

## Authors
• Marlon Aquino

