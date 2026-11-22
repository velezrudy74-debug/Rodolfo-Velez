package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.ContactMethod
import com.example.data.model.EducationItem
import com.example.data.model.ExperienceItem
import com.example.data.model.ProfileData
import com.example.data.model.ProjectCategory
import com.example.data.model.ProjectItem
import com.example.data.model.SkillCategoryGroup
import com.example.data.repository.PortfolioRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ContactFormState(
    val senderName: String = "",
    val senderEmail: String = "",
    val subject: String = "",
    val message: String = "",
    val isSending: Boolean = false
)

class PortfolioViewModel(
    private val repository: PortfolioRepository = PortfolioRepository()
) : ViewModel() {

    val profile: StateFlow<ProfileData> = repository.profile

    private val _currentTab = MutableStateFlow(0)
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow(ProjectCategory.ALL)
    val selectedCategory: StateFlow<ProjectCategory> = _selectedCategory.asStateFlow()

    private val _showOnlyBookmarked = MutableStateFlow(false)
    val showOnlyBookmarked: StateFlow<Boolean> = _showOnlyBookmarked.asStateFlow()

    val bookmarkedIds: StateFlow<Set<String>> = repository.bookmarkedIds

    private val _showBusinessCard = MutableStateFlow(false)
    val showBusinessCard: StateFlow<Boolean> = _showBusinessCard.asStateFlow()

    private val _selectedProjectDetails = MutableStateFlow<ProjectItem?>(null)
    val selectedProjectDetails: StateFlow<ProjectItem?> = _selectedProjectDetails.asStateFlow()

    private val _contactFormState = MutableStateFlow(ContactFormState())
    val contactFormState: StateFlow<ContactFormState> = _contactFormState.asStateFlow()

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage: SharedFlow<String> = _snackbarMessage.asSharedFlow()

    val filteredProjects: StateFlow<List<ProjectItem>> = combine(
        repository.projects,
        _searchQuery,
        _selectedCategory,
        _showOnlyBookmarked,
        repository.bookmarkedIds
    ) { projects, query, category, onlyBookmarked, bookmarks ->
        projects.filter { item ->
            val matchesCategory = (category == ProjectCategory.ALL || item.category == category)
            val matchesQuery = query.isBlank() ||
                item.title.contains(query, ignoreCase = true) ||
                item.description.contains(query, ignoreCase = true) ||
                item.tags.any { it.contains(query, ignoreCase = true) }
            val matchesBookmarked = !onlyBookmarked || bookmarks.contains(item.id)
            matchesCategory && matchesQuery && matchesBookmarked
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun selectTab(index: Int) {
        _currentTab.value = index
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: ProjectCategory) {
        _selectedCategory.value = category
    }

    fun toggleShowOnlyBookmarked() {
        _showOnlyBookmarked.value = !_showOnlyBookmarked.value
    }

    fun toggleBookmark(projectId: String) {
        repository.toggleBookmark(projectId)
        val isBookmarkedNow = !bookmarkedIds.value.contains(projectId)
        viewModelScope.launch {
            _snackbarMessage.emit(
                if (isBookmarkedNow) "Project saved to your bookmarks" else "Project removed from bookmarks"
            )
        }
    }

    fun openProjectDetails(project: ProjectItem) {
        _selectedProjectDetails.value = project
    }

    fun closeProjectDetails() {
        _selectedProjectDetails.value = null
    }

    fun setBusinessCardVisible(visible: Boolean) {
        _showBusinessCard.value = visible
    }

    fun updateContactName(name: String) {
        _contactFormState.value = _contactFormState.value.copy(senderName = name)
    }

    fun updateContactEmail(email: String) {
        _contactFormState.value = _contactFormState.value.copy(senderEmail = email)
    }

    fun updateContactSubject(subject: String) {
        _contactFormState.value = _contactFormState.value.copy(subject = subject)
    }

    fun updateContactMessage(msg: String) {
        _contactFormState.value = _contactFormState.value.copy(message = msg)
    }

    fun resetContactForm() {
        _contactFormState.value = ContactFormState()
    }

    fun showFeedback(msg: String) {
        viewModelScope.launch {
            _snackbarMessage.emit(msg)
        }
    }

    fun getSkillCategories(): List<SkillCategoryGroup> = repository.getSkillCategories()

    fun getExperienceTimeline(): List<ExperienceItem> = repository.getExperienceTimeline()

    fun getEducationList(): List<EducationItem> = repository.getEducationList()

    fun getContactMethods(): List<ContactMethod> = repository.getContactMethods()
}
