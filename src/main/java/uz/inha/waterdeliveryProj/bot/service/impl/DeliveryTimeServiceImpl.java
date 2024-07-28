package uz.inha.waterdeliveryProj.bot.service.impl;

import com.pengrad.telegrambot.model.CallbackQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.inha.waterdeliveryProj.bot.constants.BotConstant;
import uz.inha.waterdeliveryProj.bot.controller.BotController;
import uz.inha.waterdeliveryProj.bot.entity.DeliveryTime;
import uz.inha.waterdeliveryProj.bot.entity.Order;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;
import uz.inha.waterdeliveryProj.bot.model.Location;
import uz.inha.waterdeliveryProj.bot.service.DeliveryTimeService;
import uz.inha.waterdeliveryProj.bot.service.OrderService;
import uz.inha.waterdeliveryProj.bot.utils.DistanceUtil;
import uz.inha.waterdeliveryProj.repo.DeliveryTimeRepository;
import uz.inha.waterdeliveryProj.repo.TelegramUserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class DeliveryTimeServiceImpl implements DeliveryTimeService {
    private final DeliveryTimeRepository deliveryTimeRepository;
    private final OrderService orderService;
    private final DistanceUtil distanceUtil;

    @Override
    public List<DeliveryTime> findAll() {
        return deliveryTimeRepository.findAll();
    }

    @Override
    public List<DeliveryTime> saveAll(List<DeliveryTime> deliveryTimes) {
        return deliveryTimeRepository.saveAll(deliveryTimes);
    }

    @Override
    public boolean canFitToday(DeliveryTime deliveryTime, TelegramUser tgUser) {
        List<Order> orders = orderService.findAllByTelegramUserDistrictAndDeliveryTimeAndDay(
                tgUser.getDistrict(),
                deliveryTime,
                LocalDate.now());
        List<Location> locations = orders.stream().map(Order::getLocation).toList();

        String res = distanceUtil.buildDirectionsApiUrl(locations, COMPANY_LOCATION);
        int[] optimizedOrder = distanceUtil.getOptimizedOrder(res);
        System.out.println(Arrays.toString(optimizedOrder));

        long totalTimeInSeconds = distanceUtil.getTotalTime(res);
        long totalTimeInMinutes = totalTimeInSeconds / 60;
        totalTimeInMinutes += 15L * locations.size();
        return deliveryTime.getStartTime().plusMinutes(totalTimeInMinutes).isBefore(deliveryTime.getEndTime());
    }

    @Override
    public boolean deliveryTimeIsNow(DeliveryTime deliveryTime) {
        LocalTime now = LocalTime.now();
        return now.isBefore(deliveryTime.getStartTime());
    }

    @Override
    public DeliveryTime findById(int i) {
        return deliveryTimeRepository.findById(i).orElseThrow(() -> new RuntimeException("Delivery Time not found"));
    }

    @Override
    public List<LocalDate> generateDays() {
        return List.of(
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2)
        );
    }

    @Override
    public String getDayAsText(LocalDate day) {
        if (day.equals(LocalDate.now())) {
            return BotConstant.TODAY;
        } else if (day.equals(LocalDate.now().plusDays(1))) {
            return BotConstant.TOMORROW;
        }
        return day.getDayOfWeek().getDisplayName(
                TextStyle.FULL, Locale.forLanguageTag("en")
        );
    }

    @Override
    public LocalDate extractDayFromCallbackQuery(CallbackQuery callbackQuery) {
        String str = callbackQuery.data().split("/")[0];
        return LocalDate.parse(str);
    }

    @Override
    public DeliveryTime extractDeliveryTimeFromCallbackQuery(CallbackQuery callbackQuery) {
        String str = callbackQuery.data().split("/")[1];
        return findById(Integer.parseInt(str));
    }
}
