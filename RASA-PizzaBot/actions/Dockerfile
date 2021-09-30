FROM rasa/rasa-sdk:2.5.0


WORKDIR /app

USER root

COPY ./requirements-actions.txt ./
#copy everything in ./actions directory (your custom actions code) to /app/actions in container
COPY ./ /app/actions

RUN pip install -U pip
RUN pip install typing_extensions
RUN pip install pandas
COPY actions.py /app
RUN pip3 install -U pip
RUN pip3 install -r requirements-actions.txt
USER 1001