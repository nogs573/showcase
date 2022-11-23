```
┌──────────────────────────────┬───────────────────────────┬────────────────────────────────────┐
│       UI Layer               │     Logic Layer           │            Data Layer              │
│                              │                           │                                    │
├──────────────────────────────┼───────────────────────────┼────────────────────────────────────┤
│                              │                           │                                    │
│HomeActivity.java             │   ┌──────────────┐        │       ┌───────────────┐            │
│LoginActivity.java            │   │              │        │       │               │            │
│AccountCreationActivity.java  │   │ application  │        │       │   interfaces  │            │
│DescriptionActivity.java      │   │              │        │       │               │            │
│MovieFilterActivity.java      │   └──────────────┘        │       └───────────────┘            │
│MovieSearchActivity.java      │ ApplicationState.java 	   │   IMovieDB.java       	        │
│Quiz.java                     │ DBConnection.java         │   IUserDB.java   		        │
│QuizQuestions.java            │ Main.java                 │  				        │
│SearchPreferences.java        │                           │	    ┌──────────────┐            │
│   ┌──────────┐               │   ┌──────────────┐        │        │              │            │
│   │          │               │   │              │        │        │    hsqldb    │            │
│   │ adapters │               │   │   business   │        │        │              │            │
│   │          │               │   │              │        │        └──────────────┘            │
│   └──────────┘               │   └──────────────┘        │   MovieHSQLDB.java                 │
│ RecyclerMovieAdapter.java    │ AccessMovies.java         │   UserHSQLDB.java                  │
│			       │ AccessUsers.java          │   PersistenceException.java        │
│			       │ FilterMovies.java         │					│
│                              │ QuizChips.java		   │        ┌───────────────┐           │
│                              │ QuizLogic.java		   │        │               │           │
│                              │ ValidateEmail.java 	   │        │     stubs     │           │
│                              │ 			   │        │               │           │
│                              │                           │        └───────────────┘           │
│                              │                           │   StubMovieDB.java                 │
│                              │                           │   StubUserDB.java		        │
└──────────────────────────────┴───────────────────────────┴────────────────────────────────────┘
                           Domain Specific Objects
                  MovieModel.java         UserModel.javas	SearchPreferences.java