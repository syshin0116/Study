from django.urls import path
from . import views

urlpatterns = [
    path('', views.HomeView.as_view(), name='home'),
    path('session/<int:pk>/', views.SessionDetailView.as_view(), name='session_detail'),
    path('session/new/', views.SessionCreateView.as_view(), name='session_create'),
    path('session/<int:pk>/edit/', views.SessionUpdateView.as_view(), name='session_update'),
    path('session/<int:pk>/delete/', views.SessionDeleteView.as_view(), name='session_delete'),
    path('session/<int:pk>/track/', views.eye_tracking_view, name='eye_tracking'),
    path('session/<int:pk>/save-data/', views.save_eye_data, name='save_eye_data'),
    path('session/<int:pk>/generate-heatmap/', views.generate_heatmap, name='generate_heatmap'),
] 