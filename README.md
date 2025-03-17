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

### Vehicle Screens
<p align="center">
  <img src="screenshots/main_empty.png" alt="Empty Main Screen" width="150">
  <img src="screenshots/main_not_empty.png" alt="Main Screen with Vehicles" width="150">
  <img src="screenshots/main_search.png" alt="Search in Main Screen" width="150">
  <img src="screenshots/main_add_vehicle.png" alt="Add Vehicle Screen" width="150">
  <img src="screenshots/main_add_vehicle_completed.png" alt="Add Vehicle Completed" width="150">
  <img src="screenshots/vehicle_info.png" alt="Vehicle Info Screen" width="150">
  <img src="screenshots/vehicle_info_edit.png" alt="Edit Vehicle Info" width="150">
  <img src="screenshots/vehicle_info_delete.png" alt="Delete Vehicle Info" width="150">
</p>

### Log Screens
<p align="center">
  <img src="screenshots/log_main.png" alt="Log Main Screen" width="150">
  <img src="screenshots/log_main_not_empty.png" alt="Log Screen with Entries" width="150">
  <img src="screenshots/log_main_search.png" alt="Search in Logs" width="150">
  <img src="screenshots/log_add.png" alt="Add Log Screen" width="150">
  <img src="screenshots/log_add_completed.png" alt="Add Log Completed" width="150">
</p>

<p align="center">
  <img src="screenshots/log_info.png" alt="Log Info Screen" width="150">
  <img src="screenshots/log_info_edit.png" alt="Edit Log Info" width="150">
  <img src="screenshots/log_info_delete.png" alt="Delete Log Info" width="150">
</p>

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