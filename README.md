# FTLAV
*FTL: Faster Than Light Adventure Visualiser*

This (currently highly experimental and totally not user-ready!) application is a tool that generates a nice visualisation of your current game of FTL: Faster Than Light (www.ftlgame.com) while you play.

Large chunks of the code used in this Java-based application come from [Vhati](https://github.com/Vhati)'s version of the [ftl-profile-editor](https://github.com/Vhati/ftl-profile-editor).

Use [Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) to build from source.

### MAJOR TODO
- Proper graphs that look great, easy to manipulate, readable and in some way insightful for the user.
- Removing all classes and methods taken from ftl-profile-editor that aren't needed for this project.
- Graphs are exportable to common formats (i.e. PNG, GIF, PDF, SVG) to enable people sharing their FTL adventures with others.
- Make it possible that the application can dump the array of gamestates to a file before you quit the application so that you can resume recording data the next time you start playing FTL again.
- A cool looking and effective user interfaces inspired on the one you see in FTL.