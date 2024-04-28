# -*- encoding: utf-8 -*-
"""
Copyright (c) 2019 - present AppSeed.us
"""

from django.db import models
from django.contrib.auth.models import User


# Create your models here.
class Order(models.Model):
    user_id = models.CharField()
    order_reg_date = models.DateTimeField(auto_now_add=True)
    order_amount = models.FloatField()
    test = models.FloatField()
