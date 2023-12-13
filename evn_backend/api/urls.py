from django.urls import path

from .views import api_common

urlpatterns = [
    path('', api_common)
]