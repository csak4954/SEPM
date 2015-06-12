package at.uibk.los.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.uibk.los.AppDomain;
import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.exceptions.EntityNotFoundException;
import at.uibk.los.model.interfaces.IModel;
import at.uibk.los.model.interfaces.IStatistics;
import at.uibk.los.viewmodel.StatisticsViewModel;
import at.uibk.los.viewmodel.StatusViewModel;

@Controller
@RequestMapping("/lecture/{lectureId}/statistics")
public class StatisticsController {

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Object getFeedback(@PathVariable String lectureId, HttpServletResponse response){

		IModel model = AppDomain.get();

		try 
		{
			IStatistics statistics = model.getStatistics(lectureId);
			return StatusViewModel.onSuccess(response, new StatisticsViewModel(statistics));
		}
		catch (LOSAccessDeniedException e) {
			return StatusViewModel.onException(e, response);
		} 
		catch (EntityNotFoundException e) {
			return StatusViewModel.onException(e, response);
		}
	}
}
