package com.example.data.model

data class ProfileData(
    val fullName: String = "Rodolfo Cortez Velez",
    val preferredName: String = "Rudy Velez",
    val handle: String = "velezrudy74",
    val title: String = "Senior Software Engineer & Mobile Developer",
    val tagline: String = "Building high-performance Android & full-stack experiences with clean architecture and modern UX.",
    val location: String = "United States",
    val email: String = "velezrudy74@gmail.com",
    val githubUrl: String = "https://github.com/velezrudy74-debug",
    val repoUrl: String = "https://github.com/velezrudy74-debug/Rodolfo-Cortez-Velez",
    val availabilityStatus: String = "Available for Opportunities",
    val yearsExperience: String = "6+",
    val completedProjectsCount: String = "24+",
    val codeContributions: String = "1,400+",
    val aboutBio: String = "Hi! I'm Rudy Velez. I'm a passionate software engineer specializing in Android (Kotlin, Jetpack Compose, Clean Architecture) and modern full-stack development. I focus on creating intuitive, responsive apps that solve real user problems with elegant code and resilient system design."
)

enum class ProjectCategory(val label: String) {
    ALL("All"),
    ANDROID("Android & Mobile"),
    FULLSTACK("Full Stack & Web"),
    CLOUD_AI("Cloud & AI"),
    OPEN_SOURCE("Open Source")
}

data class ProjectItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val category: ProjectCategory,
    val tags: List<String>,
    val githubUrl: String,
    val liveDemoUrl: String? = null,
    val isFeatured: Boolean = false,
    val isBookmarked: Boolean = false,
    val keyFeatures: List<String>
)

data class SkillItem(
    val name: String,
    val levelPercent: Int,
    val tag: String
)

data class SkillCategoryGroup(
    val categoryName: String,
    val iconName: String,
    val skills: List<SkillItem>
)

data class ExperienceItem(
    val id: String,
    val role: String,
    val company: String,
    val period: String,
    val location: String,
    val description: String,
    val highlights: List<String>,
    val technologies: List<String>
)

data class EducationItem(
    val degree: String,
    val institution: String,
    val year: String,
    val honors: String
)

data class ContactMethod(
    val type: String,
    val title: String,
    val value: String,
    val actionText: String,
    val isPrimary: Boolean = false
)
