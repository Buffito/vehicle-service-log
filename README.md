# Vehicle Service Log App

## Introduction
A simple Android app built with Jetpack Compose, Room, and MVVM architecture to manage and track vehicle service logs.

## Features
- Add, view, and update service logs for different vehicles.
- Search by date, shop, or description.
- Filter active vehicles.

## Screenshots
<details>
  <summary>Click to expand</summary>

### Home Screen
<img src="screenshots/home.png" alt="Home Screen" width="150">

### Add/Edit Vehicle Screen
<img src="screenshots/add_vehicle.png" alt="Add/Edit Vehicle Screen" width="150">

### Vehicle Info Screen
<img src="screenshots/vehicle_info.png" alt="Vehicle Info Screen" width="150">

### Vehicle Logs Screen
<img src="screenshots/vehicle_logs.png" alt="Vehicle Logs Screen" width="150">

### Add/Edit Vehicle Log Screen
<img src="screenshots/add_log.png" alt="Add/Edit Vehicle Log Screen" width="150">

### Log Info Screen
<img src="screenshots/log_info.png" alt="Log Info Screen" width="150">

</details>

## Installation
1. Clone the repository:
```bash
git clone https://github.com/Buffito/vehicle-service-log.git
```
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## Usage
- Navigate to the "Vehicle List" to view and manage vehicles.
- Use the search bar to filter service logs.
- Tap on a log for detailed information.

## Architecture
- **Jetpack Compose** for UI.
- **Room Database** for local data persistence.
- **MVVM Pattern** for separation of concerns.
- **Dagger Hilt** for dependency injection.

