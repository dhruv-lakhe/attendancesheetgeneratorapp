
# Attendance Manager

A lightweight Android app to record class attendance, toggle present/absent per roll number, and export results to an Excel‑readable spreadsheet.[2]

## Overview
This project focuses on fast, offline attendance capture for a single class session with minimal setup and one‑tap export of the marked list.
The README structure follows widely recommended guidance so new contributors can quickly understand goals, setup, and contribution flow.

## Screenshots
Main screens: splash, setup (class/date/students), marking list, and sample exported sheet.

![Screenshot 1](./screens.png)


The image uses the standard Markdown image syntax that renders on GitHub.

## Features
- Set class name, pick the attendance date, and define the student count before marking.[2]
- Toggle Present/Absent for each roll number via simple radio controls in a clean list UI.[2]
- Save the session locally and export an .xls‑style spreadsheet for sharing or archival.[2]
- Floating action button provides quick access to actions such as Save or Export.[2]

## Requirements
- Android Studio with Gradle wrapper and an Android device or emulator for running the app.[1]
- Storage/media access on the device to allow writing the exported spreadsheet file.[5]

## Installation
- Clone the repository locally and open the project in Android Studio.[1]
- Allow Gradle to sync dependencies, then build and run on a device or emulator.[1]
- Command‑line build is also possible using the Gradle wrapper.[1]

```bash
./gradlew assembleDebug
```

Use this command from the project root to produce a debug build.[1]

## Usage
- On first launch, enter the number of students, provide a class name, and select the date of attendance.[2]
- Mark each roll number as Present or Absent, then tap Save to persist the session state.[2]
- Use the action menu to Export; an Excel‑readable spreadsheet with headers is generated on device storage.[2]

## Permissions
- Export requires storage/media permission so the spreadsheet can be written to shared storage.[5][2]
- On modern Android versions, request only the minimum media permissions required by the export flow as platform behavior evolved across Android 11 and 13.[6][5]
- If permission is denied, an in‑app notice appears and export will be blocked until permission is granted via the system dialog or settings.[5][2]

## Export format
- The exported sheet includes a header row and one row per roll number with Present/Absent status, date, and class name.[2]
- Files are written in an Excel‑readable .xls format that opens in common spreadsheet tools.[2]

## Project structure
A conventional Android layout helps contributors discover UI, data, and build files quickly.[7]

```
app/
  src/
    main/
      java/...        # Activities / Fragments / ViewModels
      res/...         # Layouts, drawables, strings
  build.gradle
gradle/
```

This structure mirrors standard Android templates so onboarding stays straightforward.[7]

## Roadmap
- Roster import (CSV) with named student mapping to roll numbers.[1]
- Per‑student history views and monthly summary exports for attendance analytics.[1]
- Optional cloud backup while retaining fully offline marking.[1]

