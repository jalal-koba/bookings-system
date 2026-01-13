package com.university.bookings.Entities;

public enum AppointmentStatus {
    PENDING,     // بانتظار قرار الأدمن
    APPROVED,    // تمت الموافقة
    REJECTED,    // رُفض من الأدمن
    CANCELED     // ألغاه المستخدم لاحقًا (اختياري)
}