from django.http import JsonResponse


def api_common(request, *args, **kwargs):
    return JsonResponse({"message": "Start project!"})
