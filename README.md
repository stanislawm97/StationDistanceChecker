# Station Distance Checker

An Android application for calculating the distance between two railway stations. It calculates the distance between selected stations and utilizes the Google Maps API for data visualization on the map. The application is written in Kotlin and uses the latest practices and tools from the Android ecosystem.

## Features

- Searching for railway stations by name.
- Calculating the distance between selected stations.
- Displaying results on the map.

## Used Technologies and Libraries

The application utilizes the following technologies and libraries:

- Kotlin
- Android Jetpack (Compose, ViewModel, LiveData, Room) - Room is used to store data about stations and distances locally on the device, allowing for fast access and operations even without network access.
- Google Maps API
- Retrofit - for API communication
- Koin - as a dependency management system
- Coroutines - for asynchronous operation handling
- JUnit, MockK - for testing
- and others [full list](https://github.com/stanislawm97/StationDistanceChecker/blob/main/gradle/libs.versions.toml)

## Running the Application

To run the application, you have two options:

### Option 1: Running from Source Code

To run the application, you need a Google Maps API key. Obtain it from [Create API keys](https://developers.google.com/maps/documentation/javascript/get-api-key?hl=en#create-api-keys) and add it to the `local.defaults.properties` file by replacing `MAPS_API_KEY=DEFAULT_API_KEY` with your own API key.

### Option 2: Installing the APK from Releases

You can download the latest APK version from the [Releases](https://github.com/stanislawm97/StationDistanceChecker/releases) section in the GitHub repository and install it directly on your Android device.

## Unit Testing

The application leverages JUnit5 and MockK for unit testing, focusing on the business logic to ensure the components of the application function correctly without the need for running an Android environment.

### Setting Up the Unit Testing Environment

To run the unit tests, a JDK environment is required (version 17 is recommended) along with Gradle.

### Running Unit Tests

Unit tests can be run directly from Android Studio or via the command line. Instructions for both methods are provided below:

#### Running from Android Studio

1. Open the test class, e.g., `StationSpanRepositoryTest`, in Android Studio.
2. Right-click on the class name or a specific test and select `Run 'StationSpanRepositoryTest'` or `Run 'testName'`.

#### Running from the Command Line

1. Open a terminal in the root directory of the project.
2. Execute the following command to run all unit tests:
   ```shell
   ./gradlew test
   ```

## UI Tests

The application utilizes Android Jetpack Compose for building its user interface and the `androidx.compose.ui.test` library for testing these UI components. UI tests focus on verifying user interactions with the application, such as searching for stations and calculating and displaying the distance between selected stations.

### Setting Up the Environment for UI Tests

To run the UI tests, you will need:

1. Android Studio environment with Android SDK installed.
2. An Android Emulator installed and configured or a physical device connected.

### Running UI Tests

UI tests can be run directly from Android Studio or using the command line. Below are the instructions for both methods:

#### Running from Android Studio

1. Open the test class `StationSearchDistanceTest` in Android Studio.
2. Right-click on the class name or a test method and select `Run 'StationSearchDistanceTest'` or `Run 'testSearchAndDisplayDistanceBetweenStations'`.

#### Running from Command Line

1. Open a terminal in the root directory of the project.
2. Execute the following command to run all UI tests:
   ```shell
   ./gradlew connectedAndroidTest
   ```

## Continuous Integration (CI)

The project uses GitHub Actions for automatically running unit tests for every pull request to the `main` and `dev` branches.

### CI Workflow

The CI process consists of the following steps:

- **Check out code**: Clones the repository for running GitHub actions.
- **Install Unzip**: Prepares the environment by installing necessary tools.
- **Set up JDK 17**: Configures Java Development Kit 17 for building Android projects.
- **Set up Android SDK**: Prepares the Android SDK environment for building and testing the application.
- **Grant execute permission for gradlew**: Ensures the Gradle wrapper script is executable.
- **Run Check**: Executes the `./gradlew check` command, which runs all configured checks, such as unit tests.

The workflow is defined in the `.github/workflows` directory of the repository and is triggered for every pull request to the `main` and `dev` branches. It runs on a self-hosted runner, ensuring a consistent and controlled testing environment.

### CI Environment Settings

The CI process is designed to run on a self-hosted machine, which requires manual configuration. To replicate the CI environment or configure a new self-hosted runner, ensure the following requirements are met:

1. **Self-Hosted Runner**: Follow [GitHub's documentation](https://docs.github.com/en/actions/hosting-your-own-runners) to set up a self-hosted runner.
2. **JDK 17**: Install JDK 17, which is required for building Android projects.
3. **Android SDK**: Install and configure the Android SDK with necessary tools and platforms.
4. **Environment Variables**: Set up any necessary environment variables, such as `ANDROID_HOME`, pointing to the location of the Android SDK.
