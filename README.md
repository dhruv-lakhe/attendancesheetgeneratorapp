
## Overview
Attendance Manager is a simple Android app to record daily presence for a class, toggle present/absent per roll number, and save results to a local spreadsheet.[1]
The README follows widely recommended sections so new contributors quickly understand the what, why, and how of the project.[2][3]

## Screenshots
The following composite shows the splash, configuration, marking screen, and an exported spreadsheet preview.[1]
  

## Features
- Create or select a class and date, then mark each roll number as Present or Absent with quick radio toggles.[1]
- Persist attendance locally and export to a .xls‑style spreadsheet for sharing or archival.[1]
- Simple, clean UI with a floating action button for actions like save/export.[1]

## Tech stack
- Android app built with native components and standard Material UI patterns.[1]
- Local storage for attendance data and on‑device spreadsheet file generation.[1]
- Designed for quick offline use and minimal setup for instructors.[1]

## Requirements
- Android Studio (latest stable) and an Android device or emulator.[3]
- Android SDK configured and Gradle wrapper included in the project.[3]
- File storage access enabled to write the exported spreadsheet.[1]

## Getting started
- Clone the repository into a local workspace and open it in Android Studio.[3]
- Let Gradle sync; then build and run the app on a device or emulator.[3]
- On first run, grant storage permission when prompted to enable exports.[1]

```bash
# Build & run (from Android Studio or Gradle)
./gradlew assembleDebug
```

## Usage
- Enter number of students, class name, and pick the attendance date from the inline date selectors.[1]
- Tap each roll number to set Present/Absent, then press Save to persist the session.[1]
- Use the action menu to export the sheet; a .xls file with headers like Student, Attendance, Date, and Class is generated.[1]

## Permissions
- The app requests storage permission to create the exported spreadsheet on device storage, which must be granted for saving to succeed.[1]
- If permission is denied, an in‑app notice appears and export will be blocked until permission is granted.[1]

## Export format
- Exports include a header row and a row per roll number with status values like Present/Absent.[1]
- Files are written in an Excel‑readable .xls format for easy opening and sharing.[1]

## Project structure
A conventional Android project layout is recommended so newcomers can navigate modules, UI, and data layers effectively.[4][3]
Consider separating UI, domain, and data concerns to keep features like marking and exporting loosely coupled.[4]

```
app/
  src/
    main/
      java/...        # activities/fragments/viewmodels [7]
      res/...         # layouts, drawables, strings [7]
  build.gradle        # app configuration [7]
gradle/               # gradle wrapper and scripts [7]
```

## Contributing
Keep pull requests focused, include clear descriptions, and update the README and in‑app help when adding or changing features.[3]
Adopt consistent code style and add tests where feasible to sustain quality over time.[4]

## Roadmap
- Class roster import (CSV) and named‑student mapping to roll numbers.[3]
- Multi‑class management, per‑student history, and monthly summary exports.[3]
- Optional cloud backup of attendance sheets while retaining offline support.[3]

## Acknowledgments
This README uses structure and guidance from professional README best‑practice resources to stay clear and actionable.[5][2][3]
Badge ideas and status indicators can be added later using Shields‑style badges if desired.[6][7]

