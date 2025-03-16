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
![Home Screen](screenshots/home.png)

### Add/Edit Vehicle Screen
![Add/Edit Vehicle](screenshots/add_vehicle.png)

### Vehicle Info Screen
![Vehicle Info Screen](screenshots/vehicle_info.png)

### Vehicle Logs Screen
![Vehicle Logs Screen](screenshots/vehicle_logs.png)

### Add/Edit Vehicle Log Screen
![Add/Edit Vehicle Log Screen](screenshots/add_log.png)

### Log Info Screen
![Log Info Screen](screenshots/log_info.png)
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

