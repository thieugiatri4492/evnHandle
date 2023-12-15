from .models import S_Users
from rest_framework import serializers


class S_UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = S_Users
        fields = ['ma_dviqly', 'userid', 'username', 'password', 'fullname']
