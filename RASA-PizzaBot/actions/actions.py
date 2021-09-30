# This files contains your custom actions which can be used to run
# custom Python code.
#
# See this guide on how to implement these action:
# https://rasa.com/docs/rasa/custom-actions


# This is a simple example for a custom action which utters "Hello World!"

from typing import Any, Text, Dict, List

from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher


class Actionpizzaform(Action):

    def name(self) -> Text:
        return "action_pizza_form"

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

            pizza = tracker.get_slot("WHAT_PIZZA_WOULD_YOU_LIKE_TO_ORDER")
            name = tracker.get_slot("WHAT_IS_YOUR_NAME")
            add = tracker.get_slot("WHAT_IS_YOUR_ADDRESS")

            dispatcher.utter_message(response = "utter_gimik")


        

        
