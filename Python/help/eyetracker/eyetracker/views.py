import cv2
import numpy as np
from django.shortcuts import render, redirect, get_object_or_404
from django.views.generic import ListView, DetailView, CreateView, UpdateView, DeleteView
from django.contrib.auth.mixins import LoginRequiredMixin
from django.contrib.auth.decorators import login_required
from django.urls import reverse_lazy
from django.http import JsonResponse
from .models import EyeTrackingSession, EyeTrackingData, Heatmap
from .forms import EyeTrackingSessionForm
from PIL import Image
import json
from scipy.ndimage import gaussian_filter

class HomeView(ListView):
    model = EyeTrackingSession
    template_name = 'eyetracker/home.html'
    context_object_name = 'sessions'
    
    def get_queryset(self):
        if self.request.user.is_authenticated:
            return EyeTrackingSession.objects.filter(user=self.request.user)
        return EyeTrackingSession.objects.none()

class SessionDetailView(LoginRequiredMixin, DetailView):
    model = EyeTrackingSession
    template_name = 'eyetracker/session_detail.html'
    context_object_name = 'session'
    
class SessionCreateView(LoginRequiredMixin, CreateView):
    model = EyeTrackingSession
    form_class = EyeTrackingSessionForm
    template_name = 'eyetracker/session_form.html'
    success_url = reverse_lazy('home')
    
    def form_valid(self, form):
        form.instance.user = self.request.user
        return super().form_valid(form)

class SessionUpdateView(LoginRequiredMixin, UpdateView):
    model = EyeTrackingSession
    form_class = EyeTrackingSessionForm
    template_name = 'eyetracker/session_form.html'
    success_url = reverse_lazy('home')

class SessionDeleteView(LoginRequiredMixin, DeleteView):
    model = EyeTrackingSession
    template_name = 'eyetracker/session_confirm_delete.html'
    success_url = reverse_lazy('home')

@login_required
def eye_tracking_view(request, pk):
    session = get_object_or_404(EyeTrackingSession, pk=pk, user=request.user)
    return render(request, 'eyetracker/eye_tracking.html', {'session': session})

@login_required
def save_eye_data(request, pk):
    if request.method == 'POST':
        session = get_object_or_404(EyeTrackingSession, pk=pk, user=request.user)
        data = json.loads(request.body)
        
        EyeTrackingData.objects.create(
            session=session,
            x_position=data.get('x'),
            y_position=data.get('y'),
            pupil_size=data.get('pupil_size'),
            gaze_duration=data.get('duration')
        )
        
        return JsonResponse({'status': 'success'})
    
    return JsonResponse({'status': 'error'}, status=400)

@login_required
def generate_heatmap(request, pk):
    session = get_object_or_404(EyeTrackingSession, pk=pk, user=request.user)
    data_points = session.data_points.all()
    
    if not data_points:
        return JsonResponse({'status': 'error', 'message': 'No data points found'})
    
    # Create a black image
    width, height = 1280, 720  # Default size, adjust as needed
    heatmap = np.zeros((height, width), dtype=np.float32)
    
    # Add data points to heatmap
    for point in data_points:
        x, y = int(point.x_position), int(point.y_position)
        if 0 <= x < width and 0 <= y < height:
            heatmap[y, x] += 1
    
    # Apply Gaussian filter
    heatmap = gaussian_filter(heatmap, sigma=10)
    
    # Normalize the heatmap
    if np.max(heatmap) > 0:
        heatmap = heatmap / np.max(heatmap)
    
    # Apply colormap
    heatmap_colored = cv2.applyColorMap((heatmap * 255).astype(np.uint8), cv2.COLORMAP_JET)
    
    # Save heatmap
    heatmap_img = Image.fromarray(cv2.cvtColor(heatmap_colored, cv2.COLOR_BGR2RGB))
    
    # Save heatmap to model
    import io
    from django.core.files.base import ContentFile
    
    buffer = io.BytesIO()
    heatmap_img.save(buffer, format='PNG')
    
    # Check if heatmap already exists
    try:
        existing_heatmap = session.heatmap
        existing_heatmap.image.delete()
        existing_heatmap.image.save(f'heatmap_{session.id}.png', ContentFile(buffer.getvalue()))
    except Heatmap.DoesNotExist:
        heatmap_obj = Heatmap(session=session)
        heatmap_obj.image.save(f'heatmap_{session.id}.png', ContentFile(buffer.getvalue()))
    
    return JsonResponse({'status': 'success', 'url': session.heatmap.image.url}) 