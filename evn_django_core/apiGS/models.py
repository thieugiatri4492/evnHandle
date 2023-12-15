from django.db import models


# Create your models here.
class S_Users(models.Model):
    ma_dviqly = models.CharField(max_length=120)
    userid = models.TextField(primary_key=True)
    username = models.CharField(max_length=120)
    password = models.CharField(max_length=120)
    fullname = models.CharField(max_length=120)

    class Meta:
        managed = False
        db_table = 'S_USER'
