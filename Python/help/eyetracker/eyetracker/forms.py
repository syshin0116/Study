from django import forms
from .models import EyeTrackingSession

class EyeTrackingSessionForm(forms.ModelForm):
    class Meta:
        model = EyeTrackingSession
        fields = ['name', 'description']
        widgets = {
            'description': forms.Textarea(attrs={'rows': 4}),
        } 