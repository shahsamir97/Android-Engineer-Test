### Task Overview

Your task is to create a simple “Weather App” using Kotlin that displays the current weather for a city. The app should allow users to:

1.	Input a city name.
2.	Fetch and display the current weather for that city using a weather API.

#### API

1. Use the OpenWeatherMap API or any other free weather API of your choice.
2. You’ll need to sign up and get an API key to access the weather data.

#### Requirements

1. UI: Use ConstraintLayout or Jetpack Compose to create a minimal UI with:
    - An input field for entering a city name.
    -  A button to fetch the weather.
    - A view to display the weather information (e.g., temperature, weather condition).
5. Networking: Use Retrofit or OkHttp to make network requests to the weather API.
6. State Management: Use LiveData or StateFlow to handle the app’s state and display the weather data.
7. Error Handling: Handle cases where the API request fails (e.g., invalid city name, network error).

#### Design

<img width="430" alt="Default view" src="https://github.com/user-attachments/assets/b68f397f-54e3-4e00-bb4d-800c19127e52">
<img width="430" alt="Modal Open" src="https://github.com/user-attachments/assets/db768609-3beb-4cb2-8aaf-13eaa65ebf66">

#### Project Structure

1. Follow MVVM architecture (Model-View-ViewModel).
2. Use Kotlin best practices for clean and maintainable code.

#### Time Limit

This task should take approximately 30 to 60 minutes to complete.

#### Evaluation Criteria

1.	Functionality: The app should fetch and display the weather for a given city.
2.	Code Quality: Proper Kotlin usage (null safety, scope functions like let, apply, etc.).
3.	UI: The UI should be clean and functional.
4.	Networking: Proper implementation of API calls with Retrofit or another networking library.
5.	State Management: Effective use of LiveData or StateFlow for managing state.
6.	Error Handling: Appropriate handling of errors when making API requests.
7.	Bonus: Implement a loading spinner while fetching the weather.

#### API Documentation

1. OpenWeatherMap API: https://openweathermap.org/api
2. Example API request:
`GET http://api.openweathermap.org/data/2.5/weather?q={cityname}&appid={API key}`


#### Instructions

1.	Fork this repository or create a new GitHub repo.
2.	Complete the test by following the requirements above.
3.	Push your code to your repository.
4.	Share the repository link once you’ve completed the task.

---

If you have any questions, feel free to send me an email.
