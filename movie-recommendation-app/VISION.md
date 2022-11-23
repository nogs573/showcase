# COMP 3350 Project Group 3 - MovieGuide

Nathan Gagné,  

Chandra Hofer,  

Mohammadafaz Munshi,  

Thomas Peters,  

Raad Mostafa,  

Brian Lim  

 ## Vision Statement

MovieGuide is a convenient place to find a personalized selection of movies. Our app will allow the user to  
get suggestions by filtering through using their preferences and information to present them a list of movies  
that they will love.  
  
The app is intended for all kinds of people who want to watch a movie, but have trouble deciding which one to  
watch. The movie quiz feature, which will be offered as soon as a user begins to use MovieGuide, is what makes  
our app so unique. It asks users some fun questions to offer a wide range of movie recommendations based on  
their personal preferences. The resulting list of movies will be determined by considering various factors such  
as genre, release date, age rating, languages, availability in the user’s country, among others. Within that list  
of recommendations, the user should be able to select any movie to read details about them.  
  
This app is designed for users of all ages, but mainly for young people. A user will be able to create an account  
and log into the app to save and update their preferences for future use. This is a convenient way for users  
to save time when wanting to explore other types of movie suggestions on the app. People who want to receive  
their movie suggestions right away can do so without creating an account by using our guest mode. Movie  
recommendations will not be saved in this mode.  
  
Our app will have many features that will enhance the user experience. There will be a search  to find information  
about movies they may have already heard about. They should be able to find those movies based on keywords  
such as their title or release date. The watchlist option in the app will let the user save movies in a must-watch  
list that they can access and edit at any time. Users will also be able to share their watchlist on social media. Also,  
they will be able to rate movies on a scale of one to five stars. Users will be the ones to decide what rating appears  
next to their favorite movies. However, only users who have created an account can use these features.

This project will be considered a success if the app becomes a common tool for people trying to find movies they  
actually want to watch. Creating watchlists and sharing your quiz results on social media will be taken as a key  
indicator in terms of assessing the popularity of this app. As users begin to rate the movies on our platform, the  
ratings will become more accurate and the app will double as an index of popular movies.

## Branching Strategy

We are following traditional git-flow for this project. We break our main branch into an umbrella development 
branch where we merge code that we work on in separate feature branches during development.

When we work on a feature, we create a new branch to perform development for that single feature. After completing 
it and reviewing the code with other team members, we then merge it with the verified code in the development branch. 
In git-flow, there are many branches for various purposes. All our features' development, as well as hotfixes, are 
done on a new branch until it's completed and then added to the development branch  Thus, the dev branch is acting 
as a "pre-main" branch during each iteration. Finally, right before releasing that iteration, it gets merged to 
the main branch.The lifetime of a mini branch is the duration it takes until one declares their work is completed. 
Mini branches adds flexibility to our group work as it allows us to work in parallel and simultaneously. Having a 
development branch as our "pre-main" branch keeps our main branch clean and organized by ensuring only thoroughly 
tested codes are inserted in order to reduce the need for maintenance in the future.
