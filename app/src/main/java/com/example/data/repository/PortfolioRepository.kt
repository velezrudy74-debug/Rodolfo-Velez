package com.example.data.repository

import com.example.data.model.ContactMethod
import com.example.data.model.EducationItem
import com.example.data.model.ExperienceItem
import com.example.data.model.ProfileData
import com.example.data.model.ProjectCategory
import com.example.data.model.ProjectItem
import com.example.data.model.SkillCategoryGroup
import com.example.data.model.SkillItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PortfolioRepository {

    private val _profile = MutableStateFlow(ProfileData())
    val profile: StateFlow<ProfileData> = _profile.asStateFlow()

    private val initialProjects = listOf(
        ProjectItem(
            id = "proj-1",
            title = "Rodolfo Cortez Velez Hub",
            subtitle = "Native Android Portfolio & Professional Showcase",
            description = "Official Android application presenting engineering projects, technical skills, career highlights, and direct communication channels built with Kotlin and Jetpack Compose.",
            category = ProjectCategory.ANDROID,
            tags = listOf("Kotlin", "Jetpack Compose", "Material 3", "Coroutines", "MVVM"),
            githubUrl = "https://github.com/velezrudy74-debug/Rodolfo-Cortez-Velez",
            isFeatured = true,
            keyFeatures = listOf(
                "Modern Material Design 3 interface with dynamic theming",
                "Direct contact dispatch and email integration",
                "Digital business card and vCard generation",
                "Interactive project catalog with instant category filtering"
            )
        ),
        ProjectItem(
            id = "proj-2",
            title = "Aether Flow Task & Goal Engine",
            subtitle = "High-productivity Android task management",
            description = "A minimalist, gesture-driven task and focus tracker designed to maximize daily output. Integrates Pomodoro intervals, analytics graphs, and offline-first persistence.",
            category = ProjectCategory.ANDROID,
            tags = listOf("Android", "Kotlin", "Room DB", "StateFlow", "Canvas"),
            githubUrl = "https://github.com/velezrudy74-debug",
            isFeatured = true,
            keyFeatures = listOf(
                "Fluid gesture controls for rapid triage",
                "Custom Compose Canvas productivity charts",
                "Offline-first reactive Room database storage"
            )
        ),
        ProjectItem(
            id = "proj-3",
            title = "PulseNet Cloud API Gateway",
            subtitle = "High-throughput asynchronous microservice",
            description = "Scalable backend service orchestrating real-time telemetry, WebSocket push updates, and rate-limited API routing with ultra-low latency.",
            category = ProjectCategory.CLOUD_AI,
            tags = listOf("Kotlin Ktor", "Docker", "PostgreSQL", "Redis", "Cloud Run"),
            githubUrl = "https://github.com/velezrudy74-debug",
            isFeatured = true,
            keyFeatures = listOf(
                "Non-blocking coroutine-based request pipeline",
                "Redis-backed token bucket rate limiting",
                "Automated CI/CD deployment container"
            )
        ),
        ProjectItem(
            id = "proj-4",
            title = "OmniSync Real-Time Dashboard",
            subtitle = "Reactive full-stack telemetry monitor",
            description = "Full-stack web application for monitoring server metrics, error logs, and distributed service health in real-time.",
            category = ProjectCategory.FULLSTACK,
            tags = listOf("TypeScript", "React", "Node.js", "WebSockets", "TailwindCSS"),
            githubUrl = "https://github.com/velezrudy74-debug",
            isFeatured = false,
            keyFeatures = listOf(
                "Sub-second live websocket metric streaming",
                "Interactive canvas data visualization",
                "Granular role-based user access control"
            )
        ),
        ProjectItem(
            id = "proj-5",
            title = "Compose Kit UI Utilities",
            subtitle = "Open source Jetpack Compose component suite",
            description = "Curated library of polished Compose components including animated glassmorphic cards, custom charts, and accessible form inputs.",
            category = ProjectCategory.OPEN_SOURCE,
            tags = listOf("Jetpack Compose", "Open Source", "Android Library", "Kotlin"),
            githubUrl = "https://github.com/velezrudy74-debug",
            isFeatured = false,
            keyFeatures = listOf(
                "Zero third-party runtime dependencies",
                "Comprehensive Robolectric test suite",
                "Detailed documentation with interactive previews"
            )
        )
    )

    private val _projects = MutableStateFlow(initialProjects)
    val projects: StateFlow<List<ProjectItem>> = _projects.asStateFlow()

    private val _bookmarkedIds = MutableStateFlow<Set<String>>(setOf("proj-1", "proj-2"))
    val bookmarkedIds: StateFlow<Set<String>> = _bookmarkedIds.asStateFlow()

    fun toggleBookmark(projectId: String) {
        _bookmarkedIds.update { current ->
            if (current.contains(projectId)) {
                current - projectId
            } else {
                current + projectId
            }
        }
    }

    fun getSkillCategories(): List<SkillCategoryGroup> = listOf(
        SkillCategoryGroup(
            categoryName = "Mobile & Android",
            iconName = "smartphone",
            skills = listOf(
                SkillItem("Kotlin", 95, "Primary"),
                SkillItem("Jetpack Compose", 92, "Core UI"),
                SkillItem("Android Architecture (MVVM/MVI)", 90, "Architecture"),
                SkillItem("Coroutines & StateFlow", 92, "Concurrency"),
                SkillItem("Room & DataStore", 88, "Persistence"),
                SkillItem("Dependency Injection", 86, "Design Pattern")
            )
        ),
        SkillCategoryGroup(
            categoryName = "Backend & Cloud",
            iconName = "cloud",
            skills = listOf(
                SkillItem("Node.js / Express", 88, "Backend"),
                SkillItem("Ktor & Spring Boot", 82, "Microservices"),
                SkillItem("REST & GraphQL APIs", 90, "Integration"),
                SkillItem("PostgreSQL & MongoDB", 85, "Database"),
                SkillItem("Docker & Containers", 84, "DevOps"),
                SkillItem("Google Cloud / Firebase", 88, "Cloud")
            )
        ),
        SkillCategoryGroup(
            categoryName = "Languages & Tools",
            iconName = "code",
            skills = listOf(
                SkillItem("Kotlin & Java", 95, "Expert"),
                SkillItem("TypeScript / JavaScript", 90, "Proficient"),
                SkillItem("Python", 82, "Scripting & Data"),
                SkillItem("Git & GitHub CI/CD", 92, "Workflow"),
                SkillItem("Gradle & Build Automation", 86, "Build System"),
                SkillItem("Unit & Integration Testing", 88, "Quality")
            )
        )
    )

    fun getExperienceTimeline(): List<ExperienceItem> = listOf(
        ExperienceItem(
            id = "exp-1",
            role = "Senior Software Engineer",
            company = "Innovative Tech Solutions",
            period = "2023 – Present",
            location = "Remote",
            description = "Spearheading modern Android development and microservice architecture. Driving technical roadmaps and mentoring junior developers.",
            highlights = listOf(
                "Modernized legacy mobile applications to Jetpack Compose, improving render performance by 35%",
                "Engineered robust offline-first caching pipelines using Room and Kotlin StateFlow",
                "Designed and implemented high-volume RESTful endpoints handling 2M+ daily requests"
            ),
            technologies = listOf("Kotlin", "Jetpack Compose", "Coroutines", "Ktor", "GCP", "CI/CD")
        ),
        ExperienceItem(
            id = "exp-2",
            role = "Full Stack Mobile Developer",
            company = "Nexus Application Systems",
            period = "2021 – 2023",
            location = "United States",
            description = "Developed customer-facing mobile applications and backend services with focus on reliability, intuitive UX, and clean APIs.",
            highlights = listOf(
                "Shipped 4 production Android applications with 4.8+ Play Store rating",
                "Built seamless real-time messaging and push notification integrations",
                "Reduced crash rate to under 0.05% through strict automated testing"
            ),
            technologies = listOf("Android SDK", "Kotlin", "Node.js", "Firebase", "PostgreSQL")
        ),
        ExperienceItem(
            id = "exp-3",
            role = "Software Engineer",
            company = "Core Development Labs",
            period = "2019 – 2021",
            location = "United States",
            description = "Built robust web dashboards, REST APIs, and automated data processing tools.",
            highlights = listOf(
                "Developed responsive front-end single-page applications with TypeScript",
                "Created automated ETL pipelines and database optimization scripts",
                "Collaborated with cross-functional teams in rapid Agile sprint cycles"
            ),
            technologies = listOf("TypeScript", "React", "Python", "SQL", "Git")
        )
    )

    fun getEducationList(): List<EducationItem> = listOf(
        EducationItem(
            degree = "Bachelor of Science in Computer Science",
            institution = "University College of Engineering",
            year = "Class of 2019",
            honors = "Focus on Systems Architecture & Mobile Computing"
        )
    )

    fun getContactMethods(): List<ContactMethod> = listOf(
        ContactMethod(
            type = "email",
            title = "Direct Email",
            value = "velezrudy74@gmail.com",
            actionText = "Send Email",
            isPrimary = true
        ),
        ContactMethod(
            type = "github",
            title = "GitHub Profile",
            value = "velezrudy74-debug",
            actionText = "Open GitHub",
            isPrimary = false
        ),
        ContactMethod(
            type = "repo",
            title = "Repository",
            value = "velezrudy74-debug/Rodolfo-Cortez-Velez",
            actionText = "View Source",
            isPrimary = false
        ),
        ContactMethod(
            type = "location",
            title = "Current Location",
            value = "United States (Available Worldwide / Remote)",
            actionText = "Remote Ready",
            isPrimary = false
        )
    )
}
