# Rudy Velez — Android Portfolio & Professional Showcase

Official Android application for **Rodolfo (Rudy) Cortez Velez**, built with modern Kotlin, Jetpack Compose, and Material Design 3.

## Overview

This native Android application serves as a comprehensive portfolio, engineering project showcase, career timeline, and direct contact hub for Rudy Velez.

### Core Features

1. **Profile & Professional Bio**:
   - Developer avatar, role title, verified badge, and current availability status.
   - Core metrics: years of engineering experience, completed projects, and GitHub contributions.
   - Interactive technical skills catalog with category filtering (Mobile & Android, Backend & Cloud, Languages & Tools) and animated proficiency indicators.

2. **Project Showcase**:
   - Searchable, filterable project library with category tags (Android & Mobile, Full Stack & Web, Cloud & AI, Open Source).
   - Detailed project dialogs covering architectural patterns, core features, and GitHub repository links.
   - Interactive project bookmarking.

3. **Career Journey & Experience**:
   - Chronological engineering timeline with achievements, leadership highlights, and tech stacks.
   - Education, academic foundations, and core engineering philosophy.

4. **Direct Contact Hub & Digital Card**:
   - Direct email integration (`velezrudy74@gmail.com`) with quick dispatch via Android `ACTION_SENDTO`.
   - In-app message composer pre-populating email drafts.
   - Digital business card modal with interactive QR code visual and vCard share intent.
   - One-tap clipboard copy for contact details and repository links.

## Architecture & Tech Stack

- **Platform**: Android SDK 36 (minSdk 24, targetSdk 36)
- **Language**: Kotlin 2.2 with AGP 9.1.1 built-in Kotlin support
- **UI Framework**: Jetpack Compose with Material 3 (M3)
- **Architecture**: MVVM with Repository Pattern and Kotlin Coroutines / `StateFlow`
- **Adaptive Launcher Icon**: Custom adaptive icon with pinned background `#0F172A` and raster fallbacks
- **Edge-to-Edge**: Full edge-to-edge support with `WindowInsets.safeDrawing`
