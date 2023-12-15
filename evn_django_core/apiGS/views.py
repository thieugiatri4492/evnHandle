from .models import S_Users
from rest_framework import  viewsets

from .serializers import S_UserSerializer


class S_UserViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = S_Users.objects.all()
    serializer_class = S_UserSerializer