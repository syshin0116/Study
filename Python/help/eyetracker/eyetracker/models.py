from django.db import models
from django.conf import settings

class EyeTrackingSession(models.Model):
    user = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    created_at = models.DateTimeField(auto_now_add=True)
    name = models.CharField(max_length=100)
    description = models.TextField(blank=True, null=True)
    
    def __str__(self):
        return f"{self.name} - {self.user.username}"

class EyeTrackingData(models.Model):
    session = models.ForeignKey(EyeTrackingSession, on_delete=models.CASCADE, related_name='data_points')
    timestamp = models.DateTimeField(auto_now_add=True)
    x_position = models.FloatField()
    y_position = models.FloatField()
    pupil_size = models.FloatField(null=True, blank=True)
    gaze_duration = models.FloatField(null=True, blank=True)
    
    class Meta:
        ordering = ['timestamp']
        
    def __str__(self):
        return f"Data at {self.timestamp}"

class Heatmap(models.Model):
    session = models.OneToOneField(EyeTrackingSession, on_delete=models.CASCADE, related_name='heatmap')
    created_at = models.DateTimeField(auto_now_add=True)
    image = models.ImageField(upload_to='heatmaps/')
    
    def __str__(self):
        return f"Heatmap for {self.session.name}" 