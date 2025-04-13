# Vehicle Service Log App

## Introduction
A simple Android app built with Jetpack Compose, Room, and MVVM architecture to manage and track vehicle service logs.

## Features
- Add, view, and update service logs for different vehicles.

## Screenshots
<details>
  <summary>Click to expand</summary>
  
### Main Screen
<p align="center">
  <img src="screenshots/main_screen.png" alt="Main Screen" width="150">
</p>

### Vehicle Screens
<p align="center">
  <img src="screenshots/vehicle/vehicle_empty.png" alt="Vehicle Main Screen" width="150">
  <img src="screenshots/vehicle/add_vehicle_empty.png" alt="Add Vehicle Screen" width="150">
  <img src="screenshots/vehicle/add_vehicle_full.png" alt="Add Vehicle Completed" width="150">
  <img src="screenshots/vehicle/vehicle_not_empty.png" alt="Vehicle Screen with Vehicles" width="150">
  <img src="screenshots/vehicle/vehicle_info.png" alt="Vehicle Info Screen" width="150">
  <img src="screenshots/vehicle/vehicle_update.png" alt="Edit Vehicle Info" width="150">
  <img src="screenshots/vehicle/vehicle_delete.png" alt="Delete Vehicle Info" width="150">
</p>

### Log Screens
<p align="center">
  <img src="screenshots/service_log/log_empty.png" alt="Log Main Screen" width="150">
  <img src="screenshots/service_log/add_log_empty.png" alt="Add Log Screen" width="150">
  <img src="screenshots/service_log/add_log_not_empty.png" alt="Add Log Completed" width="150">
  <img src="screenshots/service_log/log_not_empty.png" alt="Log Screen with Entries" width="150">
  <img src="screenshots/service_log/log_info.png" alt="Log Info Screen" width="150">
  <img src="screenshots/service_log/log_update.png" alt="Edit Log Info" width="150">
  <img src="screenshots/service_log/log_delete.png" alt="Delete Log Info" width="150">
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
- Tap on a log for detailed information.

## Architecture
- **Jetpack Compose** for UI.
- **Room Database** for local data persistence.
- **MVVM Pattern** for separation of concerns.
- **Dagger Hilt** for dependency injection.