package org.exadel.training.controller.rest;

import org.apache.poi.ss.usermodel.Workbook;
import org.exadel.training.model.CustomUserDetails;
import org.exadel.training.model.User;
import org.exadel.training.service.StatisticsService;
import org.exadel.training.service.UserService;
import org.exadel.training.utils.RoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/rest/statistics")
public class StatisticsRestController {
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, params = {"from", "to", "userId", "mask"})
    @ResponseStatus(HttpStatus.OK)
    public void getStatistics(
            HttpServletResponse response,
            @RequestParam(value = "userId") long userId,
            @RequestParam(value = "from") long fromDate,
            @RequestParam(value = "to") long toDate,
            @RequestParam(value = "mask") int mask) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();//for security in future
        if (!userDetails.hasRole(RoleUtil.ROLE_ADMIN)) {
            throw new ResourceNotFoundException();
        }
        String fileName = null;
        if (userId == 0) {
            fileName = "all_users";
        } else {
            User user = userService.getUserById(userId);
            if (user != null) {
                fileName = user.getLastName();
            } else {
                fileName = "user_not_found";
            }
        }
        Workbook workbook;
        response.setHeader("Content-Disposition", "inline; filename=statistics_" + fileName + ".xls");
        response.setContentType("application/vnd.ms-excel");
        workbook = statisticsService.getUsersStatisticsInExcel(userId, fromDate, toDate, mask);
        try {
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
